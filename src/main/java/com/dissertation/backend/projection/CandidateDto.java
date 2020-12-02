package com.dissertation.backend.projection;

import com.dissertation.backend.entity.Candidate;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "candidateNoPassword", types = {Candidate.class})
@Data
@RequiredArgsConstructor
public class CandidateDto implements CandidateNoPassword{


    private  Long id;
    private  String name;
    private  String surname;
    private  String email;
    private  byte[] profilePic;

    public CandidateDto(Long id, String name, String surname, String email, byte[] profilePic) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.profilePic = profilePic;
    }


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public byte[] getProfilePic() {
        return this.profilePic;
    }

    public void test(){
        System.out.println("manos");
    }
}
