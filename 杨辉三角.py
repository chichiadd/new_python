def generate(numRows):
    triangle = [[1]*i for i in range(1, numRows+1)]
    for i in range(numRows):
        for j in range(1, i):
            triangle[i][j] = triangle[i-1][j-1] + triangle[i-1][j]
    return triangle

def print_triangle(triangle):
    for i in range(len(triangle)):
        print("   " * (len(triangle) - i), end="")
        for j in range(len(triangle[i])):
            print("{0:6}".format(triangle[i][j]), end="")
        print()

n = int(input("请输入杨辉三角的层数："))
print_triangle(generate(n))
