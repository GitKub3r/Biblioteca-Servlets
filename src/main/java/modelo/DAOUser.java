package modelo;

import entities.Usuario;

import java.util.List;

public class DAOUser extends DAOGeneric implements EntityKit{
    public DAOUser(Class myClass, Class myClassID) {
        super(myClass, myClassID);
    }

    public Usuario getUserByEmail(String email) {
        List<Usuario> results = entityManager
                .createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                .setParameter("email", email)
                .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }



}
