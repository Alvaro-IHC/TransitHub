package com.aihc.transithub.finance.services;

import com.aihc.transithub.finance.dtos.ContributionCreateDto;
import com.aihc.transithub.finance.dtos.ContributionResponseDto;
import com.aihc.transithub.finance.dtos.ContributionUpdateDto;
import com.aihc.transithub.finance.entities.Contribution;
import com.aihc.transithub.finance.repositories.ContributionRepository;
import com.aihc.transithub.user.entities.Treasurer;
import com.aihc.transithub.user.repositories.TreasurerRepository;
import com.aihc.transithub.vehicle.entities.Vehicle;
import com.aihc.transithub.vehicle.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing contribution operations in the system.
 *
 * @author Alvaro Huanca
 */
@Service
public class ContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TreasurerRepository treasurerRepository;

    /**
     * Create a new contribution
     */
    public ContributionResponseDto createContribution(ContributionCreateDto contributionCreateDto) {
        validateContributionCreate(contributionCreateDto);

        Contribution contribution = new Contribution();
        contribution.setAmount(contributionCreateDto.getAmount());
        contribution.setPaymentDate(contributionCreateDto.getPaymentDate());
        contribution.setPaymentTime(contributionCreateDto.getPaymentTime() != null ? 
                java.time.LocalTime.parse(contributionCreateDto.getPaymentTime()) : null);
        contribution.setMonth(contributionCreateDto.getMonth());
        contribution.setYear(contributionCreateDto.getYear());
        contribution.setPayer(contributionCreateDto.getPayer());
        contribution.setConcept(contributionCreateDto.getConcept());
        contribution.setReceiptNumber(contributionCreateDto.getReceiptNumber());

        Vehicle vehicle = vehicleRepository.findById(contributionCreateDto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        contribution.setVehicle(vehicle);

        Treasurer treasurer = treasurerRepository.findById(contributionCreateDto.getTreasurerId())
                .orElseThrow(() -> new IllegalArgumentException("Treasurer not found"));
        contribution.setCollectedBy(treasurer);

        Contribution savedContribution = contributionRepository.save(contribution);
        return mapToResponseDto(savedContribution);
    }

    /**
     * Get a contribution by id
     */
    public ContributionResponseDto getContributionById(UUID id) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contribution not found, ID: " + id));
        return mapToResponseDto(contribution);
    }

    /**
     * Get all contributions
     */
    public List<ContributionResponseDto> getAllContributions() {
        return contributionRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Get contributions by treasurer ID and optional date range
     */
    public List<ContributionResponseDto> getContributionsByTreasurerIdAndDateRange(UUID treasurerId, LocalDate startDate, LocalDate endDate) {
        // Verify that the treasurer exists
        if (!treasurerRepository.existsById(treasurerId)) {
            throw new IllegalArgumentException("Treasurer not found with ID: " + treasurerId);
        }

        List<Contribution> contributions;

        if (startDate != null && endDate != null) {
            contributions = contributionRepository.findByCollectedByIdAndDateRange(treasurerId, startDate, endDate);
        } else {
            contributions = contributionRepository.findByCollectedById(treasurerId);
        }

        return contributions.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Get contributions by vehicle id
     */
    public List<ContributionResponseDto> getContributionsByVehicleId(UUID vehicleId) {
        return contributionRepository.findByVehicleId(vehicleId)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Get contributions by treasurer id
     */
    public List<ContributionResponseDto> getContributionsByTreasurerId(UUID treasurerId) {
        return contributionRepository.findByCollectedById(treasurerId)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a contribution
     */
    public ContributionResponseDto updateContribution(UUID id, ContributionUpdateDto contributionUpdateDto) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contribution not found, ID: " + id));

        if (contributionUpdateDto.getAmount() != null) {
            contribution.setAmount(contributionUpdateDto.getAmount());
        }
        if (contributionUpdateDto.getPaymentDate() != null) {
            contribution.setPaymentDate(contributionUpdateDto.getPaymentDate());
        }
        if (contributionUpdateDto.getPaymentTime() != null) {
            contribution.setPaymentTime(java.time.LocalTime.parse(contributionUpdateDto.getPaymentTime()));
        }
        if (contributionUpdateDto.getMonth() != 0) {
            contribution.setMonth(contributionUpdateDto.getMonth());
        }
        if (contributionUpdateDto.getYear() != 0) {
            contribution.setYear(contributionUpdateDto.getYear());
        }
        if (contributionUpdateDto.getPayer() != null) {
            contribution.setPayer(contributionUpdateDto.getPayer());
        }
        if (contributionUpdateDto.getConcept() != null) {
            contribution.setConcept(contributionUpdateDto.getConcept());
        }
        if (contributionUpdateDto.getReceiptNumber() != null) {
            contribution.setReceiptNumber(contributionUpdateDto.getReceiptNumber());
        }
        if (contributionUpdateDto.getVehicleId() != null) {
            Vehicle vehicle = vehicleRepository.findById(contributionUpdateDto.getVehicleId())
                    .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
            contribution.setVehicle(vehicle);
        }
        if (contributionUpdateDto.getTreasurerId() != null) {
            Treasurer treasurer = treasurerRepository.findById(contributionUpdateDto.getTreasurerId())
                    .orElseThrow(() -> new IllegalArgumentException("Treasurer not found"));
            contribution.setCollectedBy(treasurer);
        }

        Contribution updatedContribution = contributionRepository.save(contribution);
        return mapToResponseDto(updatedContribution);
    }

    /**
     * Delete a contribution by id
     */
    public void deleteContribution(UUID id) {
        if (!contributionRepository.existsById(id)) {
            throw new IllegalArgumentException("Contribution not found, ID: " + id);
        }
        contributionRepository.deleteById(id);
    }

    /**
     * Validates contribution data
     */
    private void validateContributionCreate(ContributionCreateDto contributionCreateDto) {
        if (contributionCreateDto.getAmount() == null || contributionCreateDto.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (contributionCreateDto.getPaymentDate() == null) {
            throw new IllegalArgumentException("Payment date is required");
        }
        if (contributionCreateDto.getVehicleId() == null) {
            throw new IllegalArgumentException("Vehicle ID is required");
        }
        if (contributionCreateDto.getTreasurerId() == null) {
            throw new IllegalArgumentException("Treasurer ID is required");
        }
    }

    /**
     * Maps Contribution entity to ContributionResponseDto
     */
    private ContributionResponseDto mapToResponseDto(Contribution contribution) {
        return ContributionResponseDto.builder()
                .id(contribution.getId())
                .amount(contribution.getAmount())
                .paymentDate(contribution.getPaymentDate())
                .paymentTime(contribution.getPaymentTime())
                .month(contribution.getMonth())
                .year(contribution.getYear())
                .payer(contribution.getPayer())
                .concept(contribution.getConcept())
                .receiptNumber(contribution.getReceiptNumber())
                .vehicleId(contribution.getVehicle().getId())
                .treasurerId(contribution.getCollectedBy().getId())
                .build();
    }
}
