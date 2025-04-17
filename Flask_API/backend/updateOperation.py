from flask import jsonify
import sqlite3
from readOperation import  getSpecificUser, getSpecificProduct,getSpecificOrderDetails

def update_approve_user(userId, approve):
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
            UPDATE Users SET isApproved = ? WHERE user_id = ?
        ''',
        (approve, userId)
    )
    conn.commit()
    conn.close()
    

def updateUserAllDetails(userId, name, address, email, phoneNumber, pincode,password):
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    user = getSpecificUser(userId=userId)
    if name:
        user["name"] = name
    if address:
        user["address"] = address
    if email:
        user["email"] = email
    if phoneNumber:
        user["phoneNumber"] = phoneNumber
    if pincode:
        user["pincode"] = pincode
    if password:
        user["password"] = password
    cursor.execute(
        '''
            UPDATE Users SET password = ?, name = ?, address = ?, email = ?,phoneNumber = ?,pincode = ?  where user_id = ?
        ''',
        (user["password"],user["name"],user["address"],user["email"],user["phoneNumber"],user["pincode"],userId)
    )
    conn.commit()
    conn.close()
    
# Product
    
def updateProduct(productId, name, price, category, stock):
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    product = getSpecificProduct(productId=productId)
    if name:
        product["name"] = name
    if price:
        product["price"] = price
    if category:
        product["category"] = category
    if stock:
        product["stock"] = stock
    cursor.execute(
        '''
            UPDATE Products SET name = ?, price = ?, category = ?, stock = ? WHERE product_id = ?
        ''',
        (product["name"], product["price"], product["category"], product["stock"], productId)
    )
    conn.commit()
    conn.close()
    
    
# Order    
    
def updateOrderApproval(orderId, isApproved):
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
            UPDATE Order_Details SET isApproved = ? WHERE order_id = ?
        ''',
        (isApproved, orderId)
    )
    conn.commit()
    conn.close()
    
def updateOrderDetails(user_id, product_id,quantity, price,message,category, orderId):
    try:
        conn = sqlite3.connect("my_medicalShop.db")
        cursor = conn.cursor()
        orderDetails = getSpecificOrderDetails(orderId=orderId)
        if orderDetails is None:
            return jsonify({"message": "Order not found", "status": 404})
        if quantity:
            orderDetails["quantity"] = quantity
            orderDetails["total_amount"] = int(orderDetails["quantity"]) * float(orderDetails["price"])
        if price:
            orderDetails["price"] = price
            orderDetails["total_amount"] = int(orderDetails["quantity"]) * float(orderDetails["price"])
        if message:
            orderDetails["message"] = message
        if category:
            orderDetails["category"] = category
        if user_id:
            user = getSpecificUser(userId=user_id)
            if user:
                orderDetails["user_id"] = user_id
                orderDetails["user_name"] = user["name"]
            else:
                return jsonify({"message": "User not found", "status": 404})
        if product_id:
            product = getSpecificProduct(productId=product_id)
            if product:
                orderDetails["product_id"] = product_id
                orderDetails["product_name"] = product["name"]
            else:
                return jsonify({"message": "Product not found", "status": 404})
        cursor.execute(
            '''
                UPDATE Order_Details SET user_id = ?, product_id = ?, quantity = ?, price = ?, message = ?, category = ?, total_amount = ?, product_name = ?, user_name = ? WHERE order_id = ?
            ''',
            (orderDetails["user_id"], orderDetails["product_id"], orderDetails["quantity"], orderDetails["price"], orderDetails["message"], orderDetails["category"], orderDetails["total_amount"], orderDetails["product_name"], orderDetails["user_name"], orderId)
        )
        conn.commit()
        return jsonify({"message": orderId, "status": 200})
    except Exception as error:
        return jsonify({"message": str(error), "status": 400})
    
def updateSellHistory(sell_id,quantity,remaning_stock,total_amount,price):
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
            UPDATE Sell_History SET quantity = ?, remaning_stock = ?, total_amount = ?, price = ? WHERE sell_id = ?
        ''',
        (quantity, remaning_stock, total_amount, price, sell_id)
    )
    conn.commit()
    conn.close()
def updateAvailableProducts(product_id, name, price, category, stock):
    conn = sqlite3.connect("my_medicalShop.db")
    cursor = conn.cursor()
    cursor.execute(
        '''
            UPDATE Available_Products SET name = ?, price = ?, category = ?, stock = ? WHERE product_id = ?
        ''',
        (name, price, category, stock, product_id)
    )
    conn.commit()
    conn.close()