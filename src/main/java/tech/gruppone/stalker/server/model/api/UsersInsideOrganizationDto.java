package tech.gruppone.stalker.server.model.api;

import io.micrometer.core.lang.NonNull;
import java.util.List;
import lombok.Value;

@Value
public class UsersInsideOrganizationDto {
  int usersInside;
  List<UsersInsidePlaceDto> places;

  public UsersInsideOrganizationDto(List<UsersInsidePlaceDto> places) {
    this.usersInside =
        places.stream()
            .map(UsersInsidePlaceDto::getUsersInside)
            .reduce(0, (subtotal, element) -> subtotal + element);

    this.places = places;
  }

  @Value
  public static class UsersInsidePlaceDto {
    @NonNull Long placeId;
    int usersInside;
  }
}
