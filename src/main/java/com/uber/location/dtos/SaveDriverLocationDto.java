package com.uber.location.dtos;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveDriverLocationDto {
    private String driverId;

    private double latitude;

    private double longitude;
}
