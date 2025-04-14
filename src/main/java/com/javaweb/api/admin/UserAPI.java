package com.javaweb.api.admin;

import com.javaweb.constant.SystemConstant;
import com.javaweb.exception.ServiceException;
import com.javaweb.model.dto.PasswordDTO;
import com.javaweb.model.dto.UserDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserAPI {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<?> createUsers(@Valid @RequestBody UserDTO newUser, BindingResult bindingResult) {
        ResponseDTO response = new ResponseDTO();
        try {
            if(bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());

                response.setMessage("Validation Error");
                response.setDetail(String.join(", ", errors));
                response.setData(null);

                return ResponseEntity.badRequest().body(response);
            }

            userService.insert(newUser);
            response.setMessage("User created successfully");
            response.setDetail("New user has been added");
            response.setData(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }
        catch (Exception e) {
            response.setMessage("Error occurred");
            response.setDetail(e.getMessage());
            response.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUsers(@PathVariable("id") long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(id, userDTO));
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<String> changePasswordUser(@PathVariable("id") long id, @RequestBody PasswordDTO passwordDTO) {
        try {
            userService.updatePassword(id, passwordDTO);
            return ResponseEntity.ok(SystemConstant.UPDATE_SUCCESS);
        } catch (ServiceException e) {
            //LOGGER.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PutMapping("/password/{id}/reset")
    public ResponseEntity<UserDTO> resetPassword(@PathVariable("id") long id) {
        return ResponseEntity.ok(userService.resetPassword(id));
    }

    @PutMapping("/profile/{username}")
    public ResponseEntity<UserDTO> updateProfileOfUser(@PathVariable("username") String username, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateProfileOfUser(username, userDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUsers(@RequestBody long[] idList) {
        if (idList.length > 0) {
            userService.delete(idList);
        }
        return ResponseEntity.noContent().build();
    }
}
