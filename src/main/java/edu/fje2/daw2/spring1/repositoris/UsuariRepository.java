package edu.fje2.daw2.spring1.repositoris;

import edu.fje2.daw2.spring1.model.Usuari;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariRepository extends MongoRepository<Usuari, String> {
    Usuari findByOauthID(String oauthID);
}
