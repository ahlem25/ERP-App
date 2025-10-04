package com.iss4u.erp.services.modules.core.domain.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse {
    
    private List<UserResponse> users;
    
    private long totalElements;
    
    private int totalPages;
    
    private int currentPage;
    
    private int size;
    
    private boolean first;
    
    private boolean last;
    
    private long totalUnreadNotifications;
    
    // Méthodes utilitaires
    public boolean hasNext() {
        return !last;
    }
    
    public boolean hasPrevious() {
        return !first;
    }
    
    public int getNextPage() {
        return hasNext() ? currentPage + 1 : currentPage;
    }
    
    public int getPreviousPage() {
        return hasPrevious() ? currentPage - 1 : currentPage;
    }
}
