import math

def move(start, value):
    if math.ceil(start / 10) % 2 != 0:
        return increment_index(start, value)
    else:
        return decrement_index(start, value)

def increment_index(start, value):
    index = start
    for i in range(value + 1):
        if index == 91:
            return -1
        if index % 10 == 0 and index != 0:
            remainder = abs(value - i)
            index += 10
            for i in range(remainder - 1):
                if index == 91:
                    return -1
                index -= 1
            return index
        index += 1
    return index

def decrement_index(start, value):
    index = start
    for i in range(value + 1):
        if index == 91:
            return -1
        if (index - 1) % 10 == 0 and index != 0:
            remainder = abs(value - i)
            index += 10
            for i in range(remainder - 1):
                if index == 91:
                    return -1
                index += 1
            return index
        index -=1
    return index
