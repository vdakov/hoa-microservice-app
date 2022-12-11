package nl.tudelft.sem.template.hoa.services;

import nl.tudelft.sem.template.hoa.entitites.BoardMember;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.repositories.BoardMemberRepository;
import nl.tudelft.sem.template.hoa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final transient UserRepository userRepository;
    private final transient BoardMemberRepository boardMemberRepository;

    public UserService(UserRepository userRepository, BoardMemberRepository boardMemberRepository) {
        this.userRepository = userRepository;
        this.boardMemberRepository = boardMemberRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<BoardMember> getAllBoardMembers(Hoa a){
        return boardMemberRepository.findBoardMemberByBoard(a);
    }

    public boolean saveUser(User user){
        if(userRepository.existsById(user.getId())) return false;
        userRepository.save(user);
        return true;
    }




}
