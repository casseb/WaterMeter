package objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Route")
public class Route {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING) 
	private RouteGroup routeGroup;
	
	public Route() {
		super();
	}

	public Route(String name, RouteGroup routeGroup) {
		super();
		this.name = name;
		this.routeGroup = routeGroup;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RouteGroup getRouteGroup() {
		return routeGroup;
	}

	public void setRouteGroup(RouteGroup routeGroup) {
		this.routeGroup = routeGroup;
	}
	
	public String getCompleteWay(){
		return this.getRouteGroup().getDesc() + " - " + this.getName();
	}
	
	
	
}
