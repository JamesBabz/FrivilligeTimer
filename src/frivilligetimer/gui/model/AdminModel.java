/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Volunteer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruger
 */
public class AdminModel {

    private static AdminModel instance;
    private ObservableList<Volunteer> searchedVolunteer;
    private ObservableList<Volunteer> allVolunteerInCurrentView;

    public static AdminModel getInstance() {
        if (instance == null) {
            instance = new AdminModel();
        }
        return instance;
    }

    private AdminModel() {
        this.searchedVolunteer = FXCollections.observableArrayList();
        this.allVolunteerInCurrentView = FXCollections.observableArrayList();

    }

    public ObservableList<Volunteer> getSearchedVolunteer() {
        return searchedVolunteer;
    }

    public ObservableList<Volunteer> getAllVolunteerInCurrentView() {
        return allVolunteerInCurrentView;
    }

    public void setSearchedVolunteer(ObservableList<Volunteer> searchedVolunteer) {
        this.searchedVolunteer = searchedVolunteer;
    }

    public void setAllVolunteerInCurrentView(ObservableList<Volunteer> allVolunteerInCurrentView) {
        this.allVolunteerInCurrentView = allVolunteerInCurrentView;
    }

}
