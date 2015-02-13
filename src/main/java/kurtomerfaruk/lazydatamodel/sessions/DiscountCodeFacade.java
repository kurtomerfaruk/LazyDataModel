/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurtomerfaruk.lazydatamodel.sessions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kurtomerfaruk.lazydatamodel.entities.DiscountCode;

/**
 *
 * @author Faruk
 */
@Stateless
public class DiscountCodeFacade extends AbstractFacade<DiscountCode> {
    @PersistenceContext(unitName = "kurtomerfaruk_LazyDataModel_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DiscountCodeFacade() {
        super(DiscountCode.class);
    }
    
}
