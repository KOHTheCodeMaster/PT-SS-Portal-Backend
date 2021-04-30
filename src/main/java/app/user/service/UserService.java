package app.user.service;

import app.user.dto.UserDTO;
import app.user.entity.User;
import app.user.exceptions.UserException;
import app.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service(value = "userService")
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(UserDTO userDTO) throws UserException {

        //  Throw UserException if user with same userId already exists in DB
        if (userRepository.findById(userDTO.getUserId()).isPresent())
            throw new UserException("User.ALREADY_EXISTS");

        User user = new User();
//        user.setUserId(userDTO.getUserId());  //  Auto Generated
        user.setName(userDTO.getName());
        user.setUserRole(userDTO.getUserRole());
        user.setEmailId(userDTO.getEmailId());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        user.setPicId(userDTO.getPicId());
        userRepository.save(user);

    }

    /**
     * Validate emailId, retrieve user from DB for the given emailId & return its userDTO.
     *
     * @param emailId id corresponding to the user record of the User table in DB
     * @return userDTO for the given emailId
     * @throws UserException If emailId is null OR user is not found in DB
     */
    public UserDTO getUserByEmailId(final String emailId) throws UserException {

        if (emailId == null) throw new UserException("User.INVALID_EMAIL_ID");

        User user = userRepository.findByEmailId(emailId).orElseThrow(
                () -> new UserException("User.USER_NOT_FOUND with id: " + emailId)
        );

        return user.convertToDTO();
    }

    /**
     * Authenticate User by matching the credentials provided by user in POST Request Body
     * with the original credentials of the user stored in User Table in DB
     *
     * @param userDTO user credentials which needs to be authenticated
     * @return If successful then true, Otherwise false
     * @throws UserException If either of the userDTO, emailId, password is null
     */
    public boolean loginUser(UserDTO userDTO) throws UserException {

        validateUserDTO(userDTO);

        User user = userRepository.findByEmailId(userDTO.getEmailId()).orElseThrow(
                () -> new UserException("User.USER_NOT_FOUND")
        );

        return user.getPassword().equals(userDTO.getPassword());
    }

    public void updateUserEmailId(Integer userId, String emailId) throws UserException {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException("User.USER_NOT_FOUND")
        );

        //  Update user emailId
        user.setEmailId(emailId);

        //  save the updated user in DB
        userRepository.save(user);

    }

    public void deleteUserById(Integer userId) throws UserException {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException("User.USER_NOT_FOUND")
        );

        //  save the updated user in DB
        userRepository.delete(user);

    }

    /**
     * userDTO is INVALID for null OR < 1, Otherwise Valid
     *
     * @param userDTO id which needs to be validated
     * @throws UserException If either of the userDTO, emailId, password is null
     */
    private void validateUserDTO(UserDTO userDTO) throws UserException {
        //  Throw UserException for invalid userDTO
        if (userDTO == null || userDTO.getEmailId() == null || userDTO.getPassword() == null)
            throw new UserException("User.INVALID_USER_CREDENTIALS");
    }
}
