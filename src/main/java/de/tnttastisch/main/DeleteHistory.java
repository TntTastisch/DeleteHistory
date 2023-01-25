package de.tnttastisch.main;

import de.tnttastisch.DeleteAPI;
import de.tnttastisch.utils.log.Logger;

public class DeleteHistory {

    protected DeleteAPI deleteAPI;
    private static DeleteHistory deleteHistory;
    public static void main(String[] args) {
        deleteHistory = new DeleteHistory();
        instance().deleteAPI = new DeleteAPI();
        instance().api().thread().start();
    }

    public static DeleteHistory instance() {
        return deleteHistory;
    }

    public DeleteAPI api() {
        return deleteAPI;
    }
}