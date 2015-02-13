package kurtomerfaruk.lazydatamodel.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kurtomerfaruk.lazydatamodel.entities.Customer;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@Named(value = "customerController")
@ViewScoped
public class CustomerController extends AbstractController<Customer> {

    @Inject
    private MicroMarketController zipController;
    @Inject
    private DiscountCodeController discountCodeController;
    @Inject
    private PurchaseOrderController purchaseOrderListController;

    public CustomerController() {
        // Inform the Abstract parent controller of the concrete Customer?cap_first Entity
        super(Customer.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        zipController.setSelected(null);
        discountCodeController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the MicroMarket controller in order to
     * display its data in a dialog. This is reusing existing the existing View
     * dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareZip(ActionEvent event) {
        if (this.getSelected() != null && zipController.getSelected() == null) {
            zipController.setSelected(this.getSelected().getZip());
        }
    }

    /**
     * Sets the "selected" attribute of the DiscountCode controller in order to
     * display its data in a dialog. This is reusing existing the existing View
     * dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareDiscountCode(ActionEvent event) {
        if (this.getSelected() != null && discountCodeController.getSelected() == null) {
            discountCodeController.setSelected(this.getSelected().getDiscountCode());
        }
    }

    /**
     * Sets the "items" attribute with a collection of PurchaseOrder entities
     * that are retrieved from Customer?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for PurchaseOrder page
     */
    public String navigatePurchaseOrderList() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("PurchaseOrder_items", this.getSelected().getPurchaseOrderList());
        }
        return "/purchaseOrder/index";
    }

    @SuppressWarnings("serial")
    class lazyDataModel extends LazyDataModel<Object[]> {

        private List<Object> listeToplam;

        public List<Object> getListeToplam() {
            return listeToplam;
        }

        public void setListeToplam(List<Object> listeToplam) {
            this.listeToplam = listeToplam;
        }

        @SuppressWarnings({"unchecked", "rawtypes", "static-access"})
        @Override
        public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            List<Customer> list;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("kurtomerfaruk_LazyDataModel_war_1.0PU");
            EntityManager em = emf.createEntityManager();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
            Root<Customer> cust = query.from(Customer.class);
            List<Predicate> predicateList = new ArrayList<Predicate>();
            Predicate sorgu;
            if (sortField != null && !sortField.isEmpty()) {
                if (sortOrder.equals(SortOrder.ASCENDING)) {
                    query.orderBy(builder.asc(cust.get(sortField)));
                } else {
                    query.orderBy(builder.desc(cust.get(sortField)));
                }
            } else {
                query.orderBy(builder.asc(cust.get("customerId")));
            }
            if (!filters.isEmpty()) {
                Iterator<Map.Entry<String, Object>> iterator = filters.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    if (entry.getKey().contentEquals("globalFilter")) {
                        sorgu = builder.or(builder.like(builder.upper(cust.<String>get("id")), "%" + entry.getValue().toString().toUpperCase() + "%"), builder.like(builder.upper(cust.<String>get("modelAd")), "%" + entry.getValue().toString().toUpperCase() + "%"));
                        predicateList.add(sorgu);
                    } else {
                        if (entry.getKey().equals("zip.zipCode")) {
                            sorgu = builder.like(cust.get("zip").get("zipCode").as(String.class), "%" + entry.getValue().toString().toUpperCase() + "%");
                            predicateList.add(sorgu);
                        }else if (entry.getKey().equals("discountCode.discountCode")) {
                            sorgu = builder.like(cust.get("discountCode").get("discountCode").as(String.class), "%" + entry.getValue().toString().toUpperCase() + "%");
                            predicateList.add(sorgu);
                        }else {
                            sorgu = builder.like(builder.upper(cust.<String>get(entry.getKey())), "%" + entry.getValue().toString().toUpperCase() + "%");
                            predicateList.add(sorgu);
                        }
                        
                    }

                }
            }

            Predicate[] predicates = new Predicate[predicateList.size()];
            predicateList.toArray(predicates);
            query.where(predicates);
            this.setRowCount(em.createQuery(query).getResultList().size());
            list = em.createQuery(query).setFirstResult(first).setMaxResults(pageSize).getResultList();
            return list;
        }
    }

    //public LDM liste = new LDM();
    private lazyDataModel liste ;
    private int pageSize = 12;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void loadData() {
        liste.setPageSize(pageSize);
    }

    public LazyDataModel<Object[]> getListe() {
        if(liste==null)
            liste=new lazyDataModel();
        loadData();
        return liste;
    }

    public void setListe(lazyDataModel liste) {
        this.liste = liste;
    }

}
