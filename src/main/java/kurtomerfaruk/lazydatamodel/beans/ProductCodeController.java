package kurtomerfaruk.lazydatamodel.beans;

import kurtomerfaruk.lazydatamodel.entities.ProductCode;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "productCodeController")
@ViewScoped
public class ProductCodeController extends AbstractController<ProductCode> {

    @Inject
    private ProductController productListController;

    public ProductCodeController() {
        // Inform the Abstract parent controller of the concrete ProductCode?cap_first Entity
        super(ProductCode.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
    }

    /**
     * Sets the "items" attribute with a collection of Product entities that are
     * retrieved from ProductCode?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Product page
     */
    public String navigateProductList() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Product_items", this.getSelected().getProductList());
        }
        return "/product/index";
    }

}
