@main def main(args: String*) = 
  for{
    num <- -10 to 5
    if num < 0
  } yield num
