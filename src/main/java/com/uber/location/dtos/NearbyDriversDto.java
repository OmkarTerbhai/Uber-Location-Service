package com.uber.location.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NearbyDriversDto {
    private double latitude;
    private double longitude;
}
