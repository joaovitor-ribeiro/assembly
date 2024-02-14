package com.br.assembly.dto.schedule;

import jakarta.validation.constraints.NotBlank;

public record ScheduleRequest(@NotBlank String description) {

}
