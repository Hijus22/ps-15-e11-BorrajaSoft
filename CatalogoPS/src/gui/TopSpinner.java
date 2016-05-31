/**
* Version 1
* Author: Sergio
*/

package gui;

import javax.swing.JLayeredPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TopSpinner {
   private JLayeredPane layeredPane;
   private SpinnerModel model;

   /**
    * Creates a spinner to select the sort type of the items.
    * 
    * @param parent The panel with the items to sort.
    */
   public TopSpinner(JLayeredPane parent) {
      this.layeredPane = parent;
	  //Creating different sort options
      String[] options = { "-", "Alfabéticamente", "Precio ascendente", "Precio descendente" };
      //Setting-up the Spinner
	  this.model = new SpinnerListModel(options);
      JSpinner spinner = new JSpinner(model);
      spinner.setVisible(true);
	  //Adding a ChangeListener to our Spinner
      spinner.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            SpinnerModel modelAux = model;
         }
      });
      layeredPane.add(spinner);
   }
}
