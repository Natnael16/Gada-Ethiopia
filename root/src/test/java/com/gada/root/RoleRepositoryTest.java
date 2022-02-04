package com.gada.root;



import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
 
    @Autowired
    private RoleRepository roleRepo;

    @Test
    public void testCreateRole(){
        Role user = new Role ("USER");
        Role admin= new Role("ADMIN");
        Role client = new Role("CLIENT");

        roleRepo.saveAll(List.of(user, admin, client));

         List<Role> roles = roleRepo.findAll();
        assertThat(roles.size()).isEqualTo(3) ;
    }

}
