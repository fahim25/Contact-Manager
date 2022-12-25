package com.contactmanager.controller;

import com.contactmanager.helper.Message;
import com.contactmanager.model.Contact;
import com.contactmanager.model.User;
import com.contactmanager.repository.ContactRepository;
import com.contactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;



    //for adding common data
    @ModelAttribute
    public void addCommonData(Model model, Principal principal){

        String userName = principal.getName();
        model.addAttribute("title", "User Dashboard");

        User user = this.userRepository.getUserByUserName(userName);
        model.addAttribute("user", user);

    }

    //Dashboard handler
    @RequestMapping("/index")
    public String userDashboard(Model model, Principal principal){




        return "user/dashboard";
    }



    //open add form handler

    @GetMapping("/addcontact")
    public String openAddContract(Model model){

        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());

        return "user/addcontact";
    }

    //processing add contact form


    @PostMapping("/process-contact")
    public String processContact(@Valid @ModelAttribute Contact contact,
                                 @RequestParam("cImage")MultipartFile image,
                                 Principal principal,
                                 HttpSession session
                                 ){

        try {

//            if (bindingResult.hasErrors()){
//                model.addAttribute("contact", contact);
//                return "user/addcontact";
//            }

            String name = principal.getName();
            User user = this.userRepository.getUserByUserName(name);

            //processing and uploading file
            if (image.isEmpty()){
                contact.setcImageUrl("contact.png");
            }else {
                contact.setcImageUrl(image.getOriginalFilename());

                File file = new ClassPathResource("static/images/user").getFile();

                Path path = Paths.get(file.getAbsolutePath()+File.separator+image.getOriginalFilename());

                Files.copy(image.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

            }

            contact.setUser(user);


            user.getContactList().add(contact);
            this.userRepository.save(user);

            //message
            session.setAttribute("message",new Message("Contact added successfully!", "success"));



        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            session.setAttribute("message",new Message("Something went wrong! Try again.", "danger"));
//            model.addAttribute("contact", contact);
//            return "user/addcontact";
        }
            return "user/addcontact";
    }


    @GetMapping("/showcontacts/{page}")
    public String showContacts(@PathVariable("page") Integer page,Model model, Principal principal){
        model.addAttribute("title", "Show Contacts");

        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);


        Pageable pageable = PageRequest.of(page, 3);

        Page<Contact> contacts = this.contactRepository.findContactByUser(user.getuId(), pageable);

        model.addAttribute("contacts",contacts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", contacts.getTotalPages());

        return "user/showcontacts";
    }


    //contact details
    @GetMapping("/contact/{cId}")
    public String contactDetail(@PathVariable("cId") Integer cId, Model model, Principal principal){

        try {
            Optional<Contact> contactOptional = contactRepository.findById(cId);
            Contact contact = contactOptional.get();

            String userName = principal.getName();

            User user = this.userRepository.getUserByUserName(userName);


            if (user.getuId()==contact.getUser().getuId()){
                model.addAttribute("title", contact.getcName()+ " " + contact.getcSurName());
                model.addAttribute("contact", contact);
            }else {
                model.addAttribute("title", "Not Accessible!");
                return "user/contactdetails";
            }
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("nodata", "No data found!");
            return "user/contactdetails";
        }

        return "user/contactdetails";
    }


    //Delete
    @GetMapping("/delete/{cId}")
    public String deleteContact(@PathVariable("cId") Integer cId, Model model, Principal principal, HttpSession session){


       try {
           Optional<Contact> contactOptional = this.contactRepository.findById(cId);
           Contact contact = contactOptional.get();
           String userName = principal.getName();
           User user = this.userRepository.getUserByUserName(userName);

           if (user.getuId()==contact.getUser().getuId()){
               this.contactRepository.delete(contact);
               session.setAttribute("message", new Message("Content deleted successfully!", "alert-success"));
               return "redirect:/user/showcontacts/0";
           }else {
               session.setAttribute("message", new Message("You cant access!", "alert-danger"));
               return "redirect:/user/showcontacts/0";
           }
       }catch (Exception e){
           e.printStackTrace();
           session.setAttribute("message", new Message("You cant access!", "alert-warning"));
           return "redirect:/user/showcontacts/0";
       }
    }

    //update


    @PostMapping("/update/{cId}")
    public String updateContact(@PathVariable("cId") Integer cId, Model model) {

        model.addAttribute("title", "Update Contact");


        Contact contact = this.contactRepository.findById(cId).get();

        model.addAttribute("contact", contact);

        return "user/updatecontact";
    }

    //update process

    @PostMapping("/process-update")
    public String updateProcess(@ModelAttribute Contact contact,
                                @RequestParam("cImage") MultipartFile file,
                                Model model, HttpSession session, Principal principal)
    {

        //old contact data
        Contact oldData = this.contactRepository.findById(contact.getcId()).get();

        try {

            if (!file.isEmpty()){

                //delete old pic
//                File deleteFile = new ClassPathResource("static/images/user").getFile();
//                File file1 = new File(deleteFile,oldData.getcImageUrl());
//                file1.delete();



                //update new pic
                File saveFile = new ClassPathResource("static/images/user").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());

                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

                contact.setcImageUrl(file.getOriginalFilename());

            }else {
                contact.setcImageUrl(oldData.getcImageUrl());
            }

            User user = this.userRepository.getUserByUserName(principal.getName());

            contact.setUser(user);

            this.contactRepository.save(contact);


            session.setAttribute("message", new Message("You contact is updated!", "alert-success"));


        }catch (Exception e){
            e.printStackTrace();
        }


        return "redirect:/user/contact/"+contact.getcId();
    }


}
