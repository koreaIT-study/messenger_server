package com.teamride.messenger.server.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserDTO {
    private MultipartFile file;
    private String email;
    private String name;
    private String pwd;
}
