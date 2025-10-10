package org.fourstack.loans.dto;

import java.util.List;
import java.util.Map;

public record AppContactDetails(String message,
                                Map<String, String> contactDetails,
                                List<String> onCallSupport) {
}
