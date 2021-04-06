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
import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
@Transactional
public class UserService {

    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(this.getClass());

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
        user.setEmailId(userDTO.getEmailId());
        user.setName(userDTO.getName());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setUserRole(userDTO.getUserRole());
        userRepository.save(user);

    }

    public UserDTO getUserById(Integer userId) throws UserException {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException("User.USER_NOT_FOUND")
        );

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setEmailId(user.getEmailId());
        userDTO.setName(user.getName());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setUserRole(user.getUserRole());

        return userDTO;
    }

    public boolean loginUser(UserDTO userDTO) throws UserException {

        User user = userRepository.findByEmailId(userDTO.getEmailId()).orElseThrow(
                () -> new UserException("User.USER_NOT_FOUND")
        );

        logger.info(user.toString());

        return user.getPassword().equals(userDTO.getPassword());
    }

    public List<UserDTO> getAllUsers() throws UserException {

        Iterable<User> allUsers = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : allUsers) {

            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setEmailId(user.getEmailId());
            userDTO.setName(user.getName());
            userDTO.setDateOfBirth(user.getDateOfBirth());
            userDTO.setUserRole(user.getUserRole());

            userDTOList.add(userDTO);
        }

        if (userDTOList.isEmpty())
            throw new UserException("User.USERS_NOT_FOUND");

        return userDTOList;
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
}
