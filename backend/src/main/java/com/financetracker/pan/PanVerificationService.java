package com.financetracker.pan;

import com.financetracker.model.User;
import com.financetracker.pan.model.PanVerification;
import com.financetracker.pan.repository.PanVerificationRepository;
import com.financetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PanVerificationService {

    @Autowired private PanVerificationRepository panRepo;
    @Autowired private UserRepository userRepo;

    private static final Pattern PAN_PATTERN = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

    public PanVerification verifyPan(String userEmail, String panNumber, String fullName) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        String cleanPan = panNumber != null ? panNumber.toUpperCase().trim() : "";
        boolean isValidFormat = PAN_PATTERN.matcher(cleanPan).matches();

        String status = isValidFormat ? "VERIFIED" : "FAILED";
        String name = isValidFormat ? (fullName != null ? fullName : user.getFullName()) : "Verification Failed";

        Optional<PanVerification> existing = panRepo.findByUser(user);
        PanVerification record;
        if (existing.isPresent()) {
            record = existing.get();
            record.setPanNumber(cleanPan);
            record.setVerifiedName(name);
            record.setVerificationStatus(status);
            record.setVerifiedAt(java.time.LocalDateTime.now());
        } else {
            record = new PanVerification(user, cleanPan, name, status);
        }

        return panRepo.save(record);
    }

    public Optional<PanVerification> getPanVerification(String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));
        return panRepo.findByUser(user);
    }
}
