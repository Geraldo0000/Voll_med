package med.voll.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
// criando um metodo
public class HelloController {

    //qual o metodo do protocolo http vai chamar esse metodo ( é o @GetMapping)
    @GetMapping
    public String olaMundo(){
        return "Hello Word Spring!";
    }
}
