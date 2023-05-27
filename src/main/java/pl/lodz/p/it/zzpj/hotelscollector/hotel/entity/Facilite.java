package pl.lodz.p.it.zzpj.hotelscollector.hotel.entity;

public enum Facilite {
    Upper_floors_accessible_by_elevator("Upper floors accessible by elevator"),
    Entire_unit_wheelchair_accessible("Entire unit wheelchair accessible"),
    Linen("Linen"),
    Wardrobe_or_closet("Wardrobe or closet"),
    Tea_Coffee_maker("Tea/Coffee maker"),
    Minibar("Minibar"),
    Safety_deposit_box("Safety deposit box"),
    Ironing_facilities("Ironing facilities"),
    Iron("Iron"),
    Electric_kettle("Electric kettle"),
    Desk("Desk"),
    Seating_Area("Seating Area"),
    TV("TV"),
    Telephone("Telephone"),
    Satellite_channels("Satellite channels"),
    Radio("Radio"),
    Pay_per_view_channels("Pay-per-view channels"),
    Flat_screen_TV("Flat-screen TV"),
    Socket_near_the_bed("Socket near the bed"),
    Clothes_rack("Clothes rack"),
    Wake_up_service_Alarm_clock("Wake up service/Alarm clock");

    public final String label;

    private Facilite(String label) {
        this.label = label;
    }

}
