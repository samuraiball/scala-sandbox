@main def main(args: String*) = 
  for{
    word <- Array("hello", "how", "are", "you")
    if word.contains("o")
    char <- word.chars.toArray
    if char == 'h'
    wnum = word.length
  } println(s"$word : $wnum")
