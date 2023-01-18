package nl.tudelft.sem.template.hoa.services;

import lombok.Getter;

@Getter
public class ServiceParameterClass {
    private transient HoaService hoaService = null;
    private transient UserService userService = null;
    private transient BoardMemberService boardMemberService = null;
    private transient ConnectionService connectionService = null;

    public ServiceParameterClass() {}

    public ServiceParameterClass(HoaService hoaService) {
        this.hoaService = hoaService;
    }

    public ServiceParameterClass(HoaService hoaService, UserService userService, BoardMemberService boardMemberService) {
        this.hoaService = hoaService;
        this.userService = userService;
        this.boardMemberService = boardMemberService;
    }

    public ServiceParameterClass(HoaService hoaService, ConnectionService connectionService,
            BoardMemberService boardMemberService) {
        this.hoaService = hoaService;
        this.connectionService = connectionService;
        this.boardMemberService = boardMemberService;
    }
    
}
