package br.com.todaylist.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired // <- manage to do whatever it takes.
    private UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if(user != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe");
        }
        // Here, I created a hash to encrypt the user's password.
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaHash = encoder.encode(userModel.getPassword());
        userModel.setPassword(senhaHash);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
