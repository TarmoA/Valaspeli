

object Menu {
  var sizeX = 300
  var sizeY = 200
  var x = 300
  var y = -300
  
  def inBounds(x2: Int, y2: Int) = x2 >= x && x2 <= x+ sizeX && y2 >= y + 700 && y2 <= y + 700 + sizeY
  
  
  
}