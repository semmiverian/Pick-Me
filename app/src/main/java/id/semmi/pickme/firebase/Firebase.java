package id.semmi.pickme.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public interface Firebase {
    FirebaseAuth getFirebaseAuth();
    DatabaseReference getDatabaseReference();

}
