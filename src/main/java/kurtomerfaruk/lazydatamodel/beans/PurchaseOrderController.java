package kurtomerfaruk.lazydatamodel.beans;

import kurtomerfaruk.lazydatamodel.entities.PurchaseOrder;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "purchaseOrderController")
@ViewScoped
public class PurchaseOrderController extends AbstractController<PurchaseOrder> {

    @Inject
    private ProductController productIdController;
    @Inject
    private CustomerController customerIdController;

    public PurchaseOrderController() {
        // Inform the Abstract parent controller of the concrete PurchaseOrder?cap_first Entity
        super(PurchaseOrder.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        productIdController.setSelected(null);
        customerIdController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the Product controller in order to
     * display its data in a dialog. This is reusing existing the existing View
     * dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareProductId(ActionEvent event) {
        if (this.getSelected() != null && productIdController.getSelected() == null) {
            productIdController.setSelected(this.getSelected().getProductId());
        }
    }

    /**
     * Sets the "selected" attribute of the Customer controller in order to
     * display its data in a dialog. This is reusing existing the existing View
     * dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareCustomerId(ActionEvent event) {
        if (this.getSelected() != null && customerIdController.getSelected() == null) {
            customerIdController.setSelected(this.getSelected().getCustomerId());
        }
    }
}
