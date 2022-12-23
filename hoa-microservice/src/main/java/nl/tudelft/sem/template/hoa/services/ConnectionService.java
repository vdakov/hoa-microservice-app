package nl.tudelft.sem.template.hoa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.User;
import nl.tudelft.sem.template.hoa.entitites.UserHoa;
import nl.tudelft.sem.template.hoa.repositories.ConnectionRepository;

@Service
public class ConnectionService {

    @Autowired
    private transient ConnectionRepository cRepository;

    public UserHoa createConnection(UserHoa connection) {
        return this.cRepository.save(connection);
    }

    @Transactional
    public void removeConnection(User user, Hoa hoa) {
        this.cRepository.delete(user, hoa);
    }
    
}
