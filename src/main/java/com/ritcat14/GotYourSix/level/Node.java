package com.ritcat14.GotYourSix.level;

import com.ritcat14.GotYourSix.util.Vector2i;

public class Node {
	
	public Vector2i tile = null;
	public Node parent = null;
	public double fCost = 0.0, gCost = 0.0, hCost = 0.0;
	
	public Node(Vector2i tile, Node parent, double gCost, double hCost){
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
  
  
  public boolean equals(Object o) {
    if (!(o instanceof Node)) return false;
    return tile.equals(((Node)o).tile);
  }
  public int hashCode() {
    return tile.hashCode();
  }
}
