package org.example.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.example.library.entity.Book;
import org.example.library.entity.Borrow;
import org.example.library.entity.LoginForm;
import org.example.library.entity.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties.UiService.LOGGER;

@Controller
public class HomeController {

    String jwtToken="";
    @GetMapping("/")
    public String home() {
        return "HomePage";
    }
    @GetMapping("/register/myuser")
    public String admin() {
        return "AdminRegistrationPage";
    }
    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    RestTemplate restTemplate=  new RestTemplate();

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }
    @PostMapping("/register/myuser")
    public String register(@RequestParam("name") String name,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("role") String role) {

            MyUser u=new MyUser(role,email,password,name);
            restTemplate.postForObject("http://localhost:8080/user/register/", u, MyUser.class);
            return "HomePage";

    }
    @PostMapping("/authenticate")
    public String UserHome(@RequestParam("email") String email, @RequestParam("password")String password,Model model) {
        LoginForm lf = new LoginForm(email, password);
        String url = "http://localhost:8080/user/authenticate/";
        System.out.println(lf.getUsername());
        System.out.println(lf.getPassword());
        jwtToken = restTemplate.postForObject(url, lf, String.class);
//        System.out.println("***"+jwtToken);
        return "redirect:/home";
    }
    @GetMapping("home")
    public String home(Model model) {
        if (jwtToken != null && !jwtToken.isEmpty()) {

            HttpEntity<String> entity = null;
            ResponseEntity<MyUser> response = null;
            try {
                HttpHeaders headers = createHeaders(jwtToken);
                //System.out.println("JWT Token: " + jwtToken);
                entity = new HttpEntity<>(headers);
                String getdetails = "http://localhost:8080/user/mydetails/";
                response = restTemplate.exchange(getdetails, HttpMethod.GET, entity, MyUser.class);
            } catch (HttpClientErrorException e) {
                if(e.getStatusCode()== HttpStatus.UNAUTHORIZED){
                    return "redirect:/login";
                }

            }
            MyUser user = response.getBody();
            System.out.println(response.getBody());
            if (user != null && "STUDENT".equals(user.getRole())) {
                model.addAttribute("user", user);
                return "StudentMainPage";
            }
            ResponseEntity<Integer> responseEntity= null;
            try {
                String ur="http://localhost:8080/books/number/";
                responseEntity = restTemplate.exchange(ur, HttpMethod.GET,entity, Integer.class);
            } catch (HttpClientErrorException e) {
                if(e.getStatusCode()== HttpStatus.UNAUTHORIZED){
                    return "redirect:/login";
                }

            }
            model.addAttribute("num",responseEntity.getBody());
            model.addAttribute("user", user);
            String urs="http://localhost:8080/user/number/";
            ResponseEntity<Integer> responseEntit=restTemplate.exchange(urs,HttpMethod.GET,entity, Integer.class);
            model.addAttribute("nums",responseEntit.getBody());
            return "AdminMainPage";
        }
        return "redirect:/login";
    }
    @GetMapping("/details")
    public String myDetails(Model model){
        if(jwtToken!=null && !jwtToken.isEmpty()){
            HttpHeaders headers = createHeaders(jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String getdetails="http://localhost:8080/user/mydetails/";
            ResponseEntity<MyUser> response = restTemplate.exchange(getdetails, HttpMethod.GET, entity, MyUser.class);
            MyUser user=response.getBody();
            model.addAttribute("user",user);

            System.out.println(response.getBody());
            if (user != null && "STUDENT".equals(user.getRole())) {
                return "StudentDetails";
            }

            return "Details";
        }
        return "Login";
    }
    @GetMapping("/allbooks")
    public String allBooks(Model model){
            if (jwtToken != null && !jwtToken.isEmpty()) {
                HttpHeaders headers = createHeaders(jwtToken);

                HttpEntity<String> entity = new HttpEntity<>(headers);
                String getdetails = "http://localhost:8080/books/";
                ResponseEntity<List<Book>>response = restTemplate.exchange(getdetails, HttpMethod.GET, entity,new ParameterizedTypeReference<>() {});
                System.out.println(response.hasBody());
                List<Book> book = response.getBody();
                model.addAttribute("book",book);
                String url = "http://localhost:8080/user/mydetails/";
                ResponseEntity<MyUser> res = restTemplate.exchange(url, HttpMethod.GET, entity, MyUser.class);
                MyUser user = res.getBody();

                if (user != null && "STUDENT".equals(user.getRole())) {
                    return "StudentHome";
                }

                return "AdminHome";
            }
            return "Login";
        }
    @GetMapping("/addbooks")
    private String addbooks(){
        if (jwtToken != null && !jwtToken.isEmpty()) {
            return "AddBooks";
        }
        return "redirect:/login";
    }
    @PostMapping("/addbook")
    private String addBook(@RequestParam("isbn") String isbn,@RequestParam("title") String title, @RequestParam("author") String author, Model model) {
        Book b = new Book(isbn, title, author);
        System.out.println(b.getAuthor());
        if (jwtToken != null && !jwtToken.isEmpty()) {
            HttpHeaders headers = createHeaders(jwtToken);
            HttpEntity<Book> entity = new HttpEntity<>(b,headers);
            String getdetails = "http://localhost:8080/books/";
            ResponseEntity<Book> response = restTemplate.exchange(getdetails, HttpMethod.POST, entity, Book.class);
            Book book = response.getBody();
            if(book != null) {
                model.addAttribute("success", book.getIsbn());
            }
            else {
                model.addAttribute("error", "Book Aready exists");
            }

        }
        return "AddBooks";

    }
    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password,@RequestParam("newPassword") String newPassword, Model model) {
        if (jwtToken != null && !jwtToken.isEmpty()) {
            HttpHeaders headers = createHeaders(jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = "http://localhost:8080/user/mydetails/";
            ResponseEntity<MyUser> res = restTemplate.exchange(url, HttpMethod.GET, entity, MyUser.class);
            MyUser user = res.getBody();
            model.addAttribute("user",user);
                user.setPassword(newPassword);
                HttpEntity<MyUser> entity1 = new HttpEntity<>(user,headers);
                String patchurl = "http://localhost:8080/user/";
                ResponseEntity<MyUser> resp = restTemplate.exchange(patchurl, HttpMethod.PUT, entity1, MyUser.class);
                if(resp.getBody() != null) {
                    model.addAttribute("Success","Password Changed Successfully. Please Login");
                    return "Login";
                }
                model.addAttribute("error", "Wrong password");
                return "Details";

        }
        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String logoutPage() {
        if (jwtToken != null && !jwtToken.isEmpty()) {
            HttpHeaders headers = createHeaders(jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = "http://localhost:8080/user/logout/";
            restTemplate.exchange(url, HttpMethod.GET, entity, Void.class);
            jwtToken = null;
            return "redirect:/login";
        }
        return "redirect:/login";
    }
    @GetMapping("/issue")
    public String issue(@RequestParam("id") String id, Model model) {
       // System.out.println(id);
        if (jwtToken != null && !jwtToken.isEmpty()) {
            HttpHeaders headers = createHeaders(jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = "http://localhost:8080/borrow/?id="+id;
            System.out.println(url);
            ResponseEntity<String> responseEntity=restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            //System.out.println(responseEntity.getBody());
            model.addAttribute("issue",responseEntity.getBody());
            HttpEntity<String> entit = new HttpEntity<>(headers);
            String getdetails = "http://localhost:8080/books/";
            ResponseEntity<List<Book> >response = restTemplate.exchange(getdetails, HttpMethod.GET, entit, new ParameterizedTypeReference<>() {
            });
            List<Book> book = response.getBody();
            model.addAttribute("book",book);
            return "StudentHome";
        }
        return "redirect:/login";
    }
    @GetMapping("/issued")
    public String issued(Model model){
        if (jwtToken != null && !jwtToken.isEmpty()) {
            HttpHeaders headers = createHeaders(jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url="http://localhost:8080/borrow/all/";
//            ResponseEntity<List<Book> >response = restTemplate.exchange(getdetails, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
//            });
//            List<Book> book = response.getBody();
            ResponseEntity<List<Book>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>(){
            });
            System.out.println("Ramya   " +response.getBody());
            model.addAttribute("issuedBooks",response.getBody());
            return "IssuedBooks";
        }
        return "redirect:/login";

    }

    @GetMapping("issuerequests")
    public String Issued(Model model) {
        if (jwtToken != null && !jwtToken.isEmpty()) {
            HttpHeaders headers = createHeaders(jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = "http://localhost:8080/borrow/issuereq/";
            ResponseEntity<List<Borrow>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
            });
            List<Borrow> borrow = response.getBody();
            if(borrow==null || borrow.isEmpty()){
                model.addAttribute("msg","No Pending Issue Requests");
            }
            model.addAttribute("borrow", borrow);
            return "IssueRequests";
        }
        return "redirect:/login";
    }
    @GetMapping("approveissue")
    public String approveIssue(@RequestParam("id") String id, Model model) {
        if (jwtToken != null && !jwtToken.isEmpty()) {
            HttpHeaders headers = createHeaders(jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = "http://localhost:8080/borrow/approve?id=" + id;
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String res=response.getBody();
            model.addAttribute("res");
            return "redirect:/issuerequests";
        }
        return "redirect:/login";
    }
    @GetMapping("returnrequests")
    public String returned(Model model) {
        if (jwtToken != null && !jwtToken.isEmpty()) {
            HttpHeaders headers = createHeaders(jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = "http://localhost:8080/borrow/returnreq/";
            ResponseEntity<List<Borrow>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
            });
            List<Borrow> borrow = response.getBody();
            if(borrow==null || borrow.isEmpty()){
                model.addAttribute("msg","No Pending Return Requests");
            }
            model.addAttribute("borrow", borrow);
            return "ReturnRequests";
        }
        return "redirect:/login";
    }
    @GetMapping("approvereturn")
    public String approveReturn(@RequestParam("id") String id, Model model) {
        if (jwtToken != null && !jwtToken.isEmpty()) {
            HttpHeaders headers = createHeaders(jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = "http://localhost:8080/borrow/approvereturn?id=" + id;
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String res=response.getBody();
            model.addAttribute("res");
            return "redirect:/returnrequests";
        }
        return "redirect:/login";
    }
    @GetMapping("/return")
    public String returning(@RequestParam("id") String id, Model model) {
        // System.out.println(id);
        if (jwtToken != null && !jwtToken.isEmpty()) {
            HttpHeaders headers = createHeaders(jwtToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = "http://localhost:8080/borrow/return?id="+id;
            System.out.println(url);
            ResponseEntity<String> responseEntity=restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            //System.out.println(responseEntity.getBody());
            model.addAttribute("issue",responseEntity.getBody());
            HttpEntity<String> entit = new HttpEntity<>(headers);
            String getdetails = "http://localhost:8080/books/";
            ResponseEntity<List<Book> >response = restTemplate.exchange(getdetails, HttpMethod.GET, entit, new ParameterizedTypeReference<>() {
            });
            List<Book> book = response.getBody();
            model.addAttribute("book",book);
            return "redirect:/issued";
        }
        return "redirect:/login";
    }



}
