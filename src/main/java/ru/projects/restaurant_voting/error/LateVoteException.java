package ru.projects.restaurant_voting.error;

public class LateVoteException extends AppException {
    public LateVoteException(String message) {
        super(message);
    }
}
