import React from "react"
import logo from "./logo.svg"
import Cell from "./components/Cell"
import "./App.css"

class App extends React.Component {
	constructor() {
		super()
		this.state = {
			state: "none",
			origin: null,
			destination: null,
			board: [
				[
					{
						piece: {
							color: "white",
							type: "man"
						}
					},
					{
						piece: null
					},
					{
						piece: {
							color: "white",
							type: "man"
						}
					}
				],
				[
					{
						piece: {
							color: "white",
							type: "man"
						}
					},
					{
						piece: null
					},
					{
						piece: {
							color: "white",
							type: "man"
						}
					}
				],
				[
					{
						piece: {
							color: "white",
							type: "man"
						}
					},
					{
						piece: null
					},
					{
						piece: {
							color: "black",
							type: "man"
						}
					}
				]
			]
		}
	}
	componentDidMount() {
		this.connection = new WebSocket("ws://127.0.0.1:4444")

		this.connection.onopen = function() {
			console.log("Connected!")
		}

		// Log errors
		this.connection.onerror = function(error) {
			console.log("WebSocket Error " + error)
		}

		// Log messages from the server
		this.connection.onmessage = e => {
			const data = JSON.parse(e.data)
			if (data.type === "board")
				this.setState({
					board: data.board
				})
			// console.log("Server: " + e.data)
		}
	}

	onCellPress = (i, j) => {
		console.log(this.state.state)
		console.log("press", i, j)
		switch (this.state.state) {
			case "none":
				this.setState({
					origin: [i, j],
					state: "origin"
				})
				break
			case "origin":
				this.setState(
					{
						destination: [i, j],
						state: "none"
					},
					() => {
						const sendData =
							" " +
							this.state.origin[0] +
							" " +
							this.state.origin[1] +
							" " +
							this.state.destination[0] +
							" " +
							this.state.destination[1]
						this.connection.send(sendData)
					}
				)
				break
			default:
				this.setState({ state: "none" })
		}
	}
	render() {
		return (
			<div
				className="App"
				style={{
					padding: 100,
					justifyContent: "center",
					alignItems: "center",
					backgroundColor: "#f8f8ff"
				}}
			>
				<button onClick={() => this.connection.send("reset")}>
					reset
				</button>
				<div
					style={{
						height: 640,
						width: 640,
						border: "solid 1px blue"
					}}
				>
					{this.state.board.map((row, rowIndex) => (
						<div
							style={{
								flexDirection: "row",
								flex: 1,
								margin: 0,
								height: 80
							}}
						>
							{row.map((item, colIndex) => (
								<Cell
									onPress={() =>
										this.onCellPress(rowIndex, colIndex)
									}
									item={item}
									color={
										(rowIndex + colIndex) % 2 === 0
											? "#d0a645"
											: "#0d1115"
									}
									i={rowIndex}
									j={colIndex}
								/>
							))}
						</div>
					))}
				</div>
			</div>
		)
	}
}

export default App
