package sonar.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sonar.core.SonarCore;

public class PacketFlexibleOpenGui extends PacketCoords {
	public NBTTagCompound tag;
	public int windowID;

	public PacketFlexibleOpenGui() {
	}

	public PacketFlexibleOpenGui(BlockPos pos, int windowID, NBTTagCompound tag) {
		super(pos);
		this.tag = tag;
		this.windowID = windowID;
	}

	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		tag = ByteBufUtils.readTag(buf);
		windowID = buf.readInt();
	}

	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		ByteBufUtils.writeTag(buf, tag);
		buf.writeInt(windowID);
	}

	public static class Handler implements IMessageHandler<PacketFlexibleOpenGui, IMessage> {
		@Override
		public IMessage onMessage(PacketFlexibleOpenGui message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
					Object gui = SonarCore.instance.guiHandler.getClientElement(message.tag.getInteger("id"), player, player.getEntityWorld(), message.pos, message.tag);
					FMLClientHandler.instance().showGuiScreen(gui);
					player.openContainer.windowId = message.windowID;
				}
			});
			return null;
		}
	}

}
