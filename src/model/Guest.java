package model;

/**
 * Guest.java (Model)
 *
 * Represents the person making a booking.
 * Required fields (from the specification):
 *   - guest name
 *   - phone number
 *   - email
 *   - ID proof (optional)
 */
public class Guest {

    private String name;
    private String phoneNumber;
    private String email;
    private String idProof; // optional field, can be empty

    public Guest(String name, String phoneNumber, String email, String idProof) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idProof = idProof;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getIdProof() {
        return idProof;
    }

    @Override
    public String toString() {
        String proof = (idProof == null || idProof.isEmpty()) ? "N/A" : idProof;
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + email + ", ID Proof: " + proof;
    }
}
