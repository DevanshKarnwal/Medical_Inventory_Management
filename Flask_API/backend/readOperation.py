import sqlite3

def getAllUsers():
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
        SELECT * FROM Users
        '''
    )
    users = cursor.fetchall()
    userJson = []
    for user in users:
        userJson.append(
            {
            "id": user[0],
            "user_id": user[1],
            "password": user[2],
            "date_of_account_creation": user[3],
            "isApproved": user[4],
            "block": user[5],
            "name": user[6],
            "address": user[7],
            "email": user[8],
            "phoneNumber": user[9],
            "pincode": user[10]
        }
            )
    conn.close()
    return userJson

def getSpecificUser(userId):
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
        SELECT * FROM Users WHERE user_id = ?
        ''',
        (userId,)
    )
    user = cursor.fetchone()
    if(user is None):
        return None
    userJson = {
            "id": user[0],
            "user_id": user[1],
            "password": user[2],
            "date_of_account_creation": user[3],
            "isApproved": user[4],
            "block": user[5],
            "name": user[6],
            "address": user[7],
            "email": user[8],
            "phoneNumber": user[9],
            "pincode": user[10]
        }
    conn.close()
    return userJson


def getAllProducts():
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
        SELECT * FROM Products
        '''
    )
    products = cursor.fetchall()
    productJson = []
    for product in products:
        productJson.append(
            {
            "id": product[0],
            "product_id": product[1],
            "name": product[2],
            "price": product[3],
            "category": product[4],
            "stock": product[5]
        }
            )
    conn.close()
    return productJson

def getSpecificProduct(productId):
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
        SELECT * FROM Products WHERE product_id = ?
        ''',
        (productId,)
    )
    product = cursor.fetchone()
    if(product is None):
        return None
    productJson = {
            "id": product[0],
            "product_id": product[1],
            "name": product[2],
            "price": product[3],
            "category": product[4],
            "stock": product[5]
        }
    conn.close()
    return productJson


#Order_Details
def getAllOrderDetails():
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
        SELECT * FROM Order_Details
        '''
    )
    orderDetails = cursor.fetchall()
    orderDetailsJson = []
    for orderDetail in orderDetails:
        orderDetailsJson.append(
            {
            "id": orderDetail[0],
            "order_id": orderDetail[1],
            "user_id": orderDetail[2],
            "product_id": orderDetail[3],
            "isApproved": orderDetail[4],
            "quantity": orderDetail[5],
            "date_of_order_creation": orderDetail[6],
            "price": orderDetail[7],
            "total_amount": orderDetail[8],
            "product_name": orderDetail[9],
            "user_name": orderDetail[10],
            "message": orderDetail[11],
            "category": orderDetail[12]
        }
            )
    conn.close()
    return orderDetailsJson

def getSpecificOrderDetails(orderId):
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
        SELECT * FROM Order_Details WHERE order_id = ?
        ''',
        (orderId,)
    )
    orderDetail = cursor.fetchone()
    if orderDetail is None:
        conn.close()
        return None
    orderDetailsJson = {
            "id": orderDetail[0],
            "order_id": orderDetail[1],
            "user_id": orderDetail[2],
            "product_id": orderDetail[3],
            "isApproved": orderDetail[4],
            "quantity": orderDetail[5],
            "date_of_order_creation": orderDetail[6],
            "price": orderDetail[7],
            "total_amount": orderDetail[8],
            "product_name": orderDetail[9],
            "user_name": orderDetail[10],
            "message": orderDetail[11],
            "category": orderDetail[12]
        }
    conn.close()
    return orderDetailsJson

# sell
def getAllSellHistory():
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
        SELECT * FROM Sell_History
        '''
    )
    sellHistory = cursor.fetchall()
    sellHistoryJson = []
    for sell in sellHistory:
        sellHistoryJson.append(
            {
            "id": sell[0],
            "sell_id": sell[1],
            "product_id": sell[2],
            "quantity": sell[3],
            "remaning_stock": sell[4],
            "date_of_sell": sell[5],
            "total_amount": sell[6],
            "price": sell[7],
            "product_name": sell[8],
            "user_id": sell[9],
            "user_name": sell[10]
        }
            )
    conn.close()
    return sellHistoryJson

def getSpecificSellHistory(sellId):
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
        SELECT * FROM Sell_History WHERE sell_id = ?
        ''',
        (sellId,)
    )
    sellHistory = cursor.fetchone()
    if sellHistory is None:
        conn.close()
        return None
    sellHistoryJson = {
            "id": sellHistory[0],
            "sell_id": sellHistory[1],
            "product_id": sellHistory[2],
            "quantity": sellHistory[3],
            "remaning_stock": sellHistory[4],
            "date_of_sell": sellHistory[5],
            "total_amount": sellHistory[6],
            "price": sellHistory[7],
            "product_name": sellHistory[8],
            "user_id": sellHistory[9],
            "user_name": sellHistory[10]
        }
    conn.close()
    return sellHistoryJson


# Avaialbe_Products
def getAllAvailableProducts():
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
        SELECT * FROM Available_Products
        '''
    )
    availableProducts = cursor.fetchall()
    availableProductsJson = []
    for product in availableProducts:
        availableProductsJson.append(
            {
            "id": product[0],
            "product_id": product[1],
            "name": product[2],
            "price": product[3],
            "category": product[4],
            "stock": product[5],
            "user_id": product[6],
            "user_name": product[7]
        }
            )
    conn.close()
    return availableProductsJson

def getSpecificAvailableProduct(productId):
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
        SELECT * FROM Available_Products WHERE product_id = ?
        ''',
        (productId,)
    )
    availableProduct = cursor.fetchone()
    if availableProduct is None:
        conn.close()
        return None
    availableProductJson = {
            "id": availableProduct[0],
            "product_id": availableProduct[1],
            "name": availableProduct[2],
            "price": availableProduct[3],
            "category": availableProduct[4],
            "stock": availableProduct[5],
            "user_id": availableProduct[6],
            "user_name": availableProduct[7]
        }
    conn.close()
    return availableProductJson