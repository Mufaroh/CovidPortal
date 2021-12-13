package com.portal.CovidPortal.Controller;

import com.portal.CovidPortal.Models.covid_tb;
import com.portal.CovidPortal.Reposi.Cov19Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2
@RequestMapping("/covid_Tb")
@ApiResponses(value =
        {
        @io.swagger.annotations.ApiResponse(code = 400, message = "This is a bad request, please follow the API documentation for the proper request format"),
        @io.swagger.annotations.ApiResponse(code = 401, message = "Due to security constraints, your access request cannot be authorized"),
        @io.swagger.annotations.ApiResponse(code = 500, message = "The server is down. Please bear with us."),
        })

@RestController
public class ApiController
{
    @Autowired
    private Cov19Repo cov19repo;


    @GetMapping(value = "")
    public String getPage()
    {
        return "Welcome";
    }
    //Find all patients in the database
    @GetMapping(value = "covidTb")
    public List<covid_tb> covidTb()
    {
        return cov19repo.findAll();
        // return new cov19repo<>(covidTb, HttpStatus.OK);
    }

    //Posting to the database
  @PostMapping (value = "/save")
    public String savecovid_tb (@RequestBody covid_tb covidTb)
    {
       cov19repo.save(covidTb);
        return "Patient Saved";
    }

    @PutMapping( value = "/update/{Id}")
    public String updatecovid_tb (@PathVariable int Id, @RequestBody covid_tb covid_Tb)
    {

     covid_tb covupdated=  cov19repo.findById((long) Id).get();
        covupdated.setName(covid_Tb.getName());
        covupdated.setContact(covid_Tb.getContact());
        covupdated.setCond(covid_Tb.getCond());
        covupdated.setAge(covid_Tb.getAge());
        cov19repo.save(covupdated);
     return "Patient updated!";
    }

    public String deletecovid_tb (@PathVariable int Id )
    {
       covid_tb covdelete= cov19repo.findById((long) Id).get();
        cov19repo.delete(covdelete);
        return "Patient;s Record Deleted";
    }
}