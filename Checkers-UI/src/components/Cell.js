import React, { Component } from "react"

export default class Cell extends Component {
	render() {
		return (
			<button
				style={{
					width: 80,
					height: 80,
					border: "solid 0px blue",
					backgroundColor: this.props.color,
					justifyContent: "center",
					alignItems: "center"
				}}
				onClick={this.props.onPress}
			>
				{this.props.item.piece !== null ? (
					<div
						style={{
							boxShadow: "2px 2px gray",
							alignSelf: "center",
							width: 60,
							height: 60,
							borderRadius: 30,
							backgroundColor:
								this.props.item.piece.color +
								(this.props.item.piece.type === "king"
									? "44"
									: "ff")
						}}
					/>
				) : null}
			</button>
		)
	}
}
