package in.main.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import in.main.entity.Userdtls;

public interface UserDtlsRepositoty extends JpaRepository<Userdtls,Integer>{

     Userdtls findByUsername(String username);

}
