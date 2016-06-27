package com.car.service.impl;

import com.car.dao.interfaces.RolesDAO;
import com.car.entity.Roles;
import com.car.entity.Users;
import com.car.service.interfaces.RolesService;
import com.car.service.interfaces.UsersService;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class RolesServiceImpl implements RolesService
{
    @Autowired
    private RolesDAO rolesDAO;

    @Autowired
    private UsersService usersService;

    @PersistenceContext
    EntityManager em;

    @Transactional
    @Override
    public Roles addRole(Roles roles)
    {
        return rolesDAO.save(roles);
    }

    @Transactional
    @Override
    public Roles getRole(Long role_id)
    {
        Roles roles = findById(role_id);
        return roles;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Roles> getAllRoles()
    {
        return (List<Roles>) rolesDAO.findAll();
    }

    @Transactional
    @Override
    public void deleteRole(Long role_id)
    {
        Roles deletedRole = findById(role_id);
        rolesDAO.delete(deletedRole);
    }

    @Transactional
    @Override
    public void setRoleUser(Long role_id, Long user_id)
    {
        Query query = em.createQuery("SELECT r FROM roles_has_users r WHERE r.user_id = :user_id and r.role_id = :role_id");
        query.setParameter("role_id", role_id);
        query.setParameter("user_id", user_id);
        query.executeUpdate();
        //Users users =  usersService.findUserById(user_id);
    }

    @Transactional(readOnly = true)
    @Override
    public Roles findById(Long role_id)
    {
        return rolesDAO.findOne(role_id);
    }
}
