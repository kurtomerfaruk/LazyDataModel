package kurtomerfaruk.lazydatamodel.beans;

import kurtomerfaruk.lazydatamodel.entities.Product;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "productController")
@ViewScoped
public class ProductController extends AbstractController<Product> {

    @Inject
    private ProductCodeController productCodeController;
    @Inject
    private ManufacturerController manufacturerIdController;
    @Inject
    private PurchaseOrderController purchaseOrderListController;

    public ProductController() {
        // Inform the Abstract parent controller of the concrete Product?cap_first Entity
        super(Product.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        productCodeController.setSelected(null);
        manufacturerIdController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the ProductCode controller in order to
     * display its data in a dialog. This is reusing existing the existing View
     * dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareProductCode(ActionEvent event) {
        if (this.getSelected() != null && productCodeController.getSelected() == null) {
            productCodeController.setSelected(this.getSelected().getProductCode());
        }
    }

    /**
     * Sets the "selected" attribute of the Manufacturer controller in order to
     * display its data in a dialog. This is reusing existing the existing View
     * dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareManufacturerId(ActionEvent event) {
        if (this.getSelected() != null && manufacturerIdController.getSelected() == null) {
            manufacturerIdController.setSelected(this.getSelected().getManufacturerId());
        }
    }

    /**
     * Sets the "items" attribute with a collection of PurchaseOrder entities
     * that are retrieved from Product?cap_first and returns the navigation
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

}
