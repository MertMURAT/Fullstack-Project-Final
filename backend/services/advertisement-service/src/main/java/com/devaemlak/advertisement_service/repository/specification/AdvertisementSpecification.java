package com.devaemlak.advertisement_service.repository.specification;

import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.request.AdvertisementSearchRequest;
import com.devaemlak.advertisement_service.model.Advertisement;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdvertisementSpecification {

    public static Specification<Advertisement> initAdvertisementSpecification(AdvertisementSearchRequest request) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();
            CriteriaQuery<Advertisement> criteriaQuery = criteriaBuilder.createQuery(Advertisement.class);

            if (request.getAddress() != null && !request.getAddress().isEmpty()) {
                predicateList.add(criteriaBuilder.like(root.get("address"), "%" + request.getAddress() + "%"));
            }
            if (request.getArea() > 0) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("area"), request.getArea()));
            }
            if (request.getNumberOfRooms() > 0) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfRooms"), request.getNumberOfRooms()));
            }
            if (request.getFloorNumber() > 0) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("floorNumber"), request.getFloorNumber()));
            }
            if (request.getHousingType() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("housingType"), request.getHousingType()));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
