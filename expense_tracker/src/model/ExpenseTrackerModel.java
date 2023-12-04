package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ExpenseTrackerModel class represents the model component of an expense
 * tracker application.
 * It manages the data related to expenses, applies the Observer design pattern,
 * and provides methods
 * for manipulating transactions and handling state change events.
 */

public class ExpenseTrackerModel {

  // encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;
  private List<ExpenseTrackerModelListener> obs;

  // This is applying the Observer design pattern.
  // Specifically, this is the Observable class.

  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
    obs = new ArrayList<ExpenseTrackerModelListener>();
  }

  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are
    // non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }

  public List<Transaction> getTransactions() {
    // encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
    // Perform input validation
    if (newMatchedFilterIndices == null) {
      throw new IllegalArgumentException("The matched filter indices list must be non-null.");
    }
    for (Integer matchedFilterIndex : newMatchedFilterIndices) {
      if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
        throw new IllegalArgumentException(
            "Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
      }
    }
    // For encapsulation, copy in the input list
    this.matchedFilterIndices.clear();
    this.matchedFilterIndices.addAll(newMatchedFilterIndices);
    stateChanged();
  }

  public List<Integer> getMatchedFilterIndices() {
    // For encapsulation, copy out the output list
    List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
    copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
    return copyOfMatchedFilterIndices;
  }

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return If the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */
  public boolean register(ExpenseTrackerModelListener listener) {
    // For the Observable class, this is one of the methods.
    //

    if (listener != null) {
      if (containsListener(listener)) {
        return false; // if already registered return False
      }
      obs.add(listener);
      return true; // if not already registered the register and return False
    }
    return false;
  }

  /**
   * Determines the count of listeners.
   *
   * @return The size of the observers ArrayList.
   */
  public int numberOfListeners() {
    // For testing, this is one of the methods.
    return obs.size();
  }

  /**
   * Validates the presence of a listener for testing purposes.
   *
   * @param listener The ExpenseTrackerModelListener to check for in the observers
   *                 ArrayList.
   * @return True if the listener exists in the observers ArrayList; otherwise,
   *         returns False.
   */

  public boolean containsListener(ExpenseTrackerModelListener listener) {
    // For testing, this is one of the methods.
    //
    for (ExpenseTrackerModelListener ob : obs) {
      if (ob.equals(listener)) {
        return true;
      }
    }
    return false;

  }

  /**
   * Invokes the update method for each observer in the ArrayList to refresh the
   * view.
   */
  protected void stateChanged() {
    // For the Observable class, this is one of the methods.
    for (ExpenseTrackerModelListener ob : obs) {
      ob.update(this);
    }
  }
}
