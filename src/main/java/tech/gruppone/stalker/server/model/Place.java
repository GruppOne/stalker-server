package tech.gruppone.stalker.server.model;

import org.springframework.data.annotation.Id;

import lombok.NonNull;

public class Place {

  @Id
  Long id;
  @NonNull String name;
  //@NonNull Polygon polygon;
  String address;
  String city;
  String zipcode;
  String state;
  String position;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the zipCode
   */
  public String getZipcode() {
    return zipcode;
  }

  /**
   * @param zipCode the zipCode to set
   */
  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @param state the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return the position
   */
  public String getPosition() {
    return position;
  }

  /**
   * @param position the position to set
   */
  public void setPosition(String position) {
    this.position = position;
  }



/*
  public Polygon getPolygon() {
    return polygon;
  }

  public void setPolygon(Polygon polygon) {
    this.polygon = polygon;
  }

  public PlaceData getPlaceData() {
    return placeData;
  }

  public void setPlaceData(PlaceData placeData) {
    this.placeData = placeData;
  }
*/
}
