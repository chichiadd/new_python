def get_input():
    n = int(input("输入物品总数: "))
    wt = []
    val = []
    for i in range(n):
        wt.append(int(input(f"输入第{i + 1}个物品的重量: ")))
        val.append(int(input(f"输入第{i + 1}个物品的价值: ")))

    W = int(input("输入背包的最大承重: "))
    return wt, val, W, n


def greedy_knapsack(W, wt, val, n):
    # 存储性价比的列表
    ratio = [v / w for v, w in zip(val, wt)]

    # 按照性价比从大到小排序所有的物品
    index = list(range(n))
    index.sort(key=lambda i: ratio[i], reverse=True)

    # 初始化当前背包的重量及价值
    cur_wt = 0
    final_val = 0

    # 从性价比最高的物品开始，尽可能多的加入背包
    for i in index:
        if cur_wt + wt[i] <= W:
            cur_wt += wt[i]
            final_val += val[i]
        else:
            remain = W - cur_wt
            final_val += val[i] * (remain / wt[i])
            break

    return final_val
