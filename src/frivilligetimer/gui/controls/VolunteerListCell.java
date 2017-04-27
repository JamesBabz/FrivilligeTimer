/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controls;

import frivilligetimer.gui.controller.VolunteerSingleCellController;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

/**
 *
 * @author James
 */
public class VolunteerListCell<TileModel> extends ListCell<TileModel>
{

    /**
     * The controller for the view we are displaying in the cell.
     */
    private VolunteerSingleCellController controller;

    /**
     * The View we display inside the Lsit Cell
     */
    private Node view;

    /**
     * Constructs a new VolunteerListCell that is a specialization of a normal
     * List Cell.
     */
    public VolunteerListCell()
    {
    }

    /**
     * Updates the list cell to its newly set item. This is where the magic
     * happens.
     *
     * @param item The item to display
     * @param empty Wether or not the cell is empty.
     */
    @Override
    protected void updateItem(TileModel item, boolean empty)
    {
        super.updateItem(item, empty);
        if (empty)
        {
            setGraphic(null); //If this ListCell should be displayed as an ampty one.
        }
        else
        {
            controller.setTileModel((frivilligetimer.gui.model.TileModel) item); //We assign a new BikeModel to display in this cell.
            setGraphic(view); // Update the graphics.
        }
    }

    /**
     * Set the controller for this ListCell.
     *
     * @param controller
     */
    public void setController(VolunteerSingleCellController controller)
    {
        this.controller = controller;
    }

    /**
     * Set's the view to display inside this ListCell.
     *
     * @param view
     */
    public void setView(Node view)
    {
        this.view = view;
    }
}
